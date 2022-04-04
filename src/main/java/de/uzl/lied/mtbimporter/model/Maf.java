package de.uzl.lied.mtbimporter.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mutation entry for cBioPortal (MAF = Mutation Annotation Format).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Maf {

    @JsonProperty("Hugo_Symbol")
    private String hugoSymbol;
    @JsonProperty("Entrez_Gene_Id")
    private String entrezGeneId;
    @JsonProperty("Center")
    private String center;
    @JsonProperty("NCBI_Build")
    private String ncbiBuild;
    @JsonProperty("Chromosome")
    private String chromosome;
    @JsonProperty("Start_Position")
    private Integer startPosition = 0;
    @JsonProperty("End_Position")
    private Integer endPosition = 0;
    @JsonProperty("Strand")
    private Character strand;
    @JsonProperty("Variant_Classification")
    private String variantClassification;
    @JsonProperty("Variant_Type")
    private String variantType;
    @JsonProperty("Reference_Allele")
    private String referenceAllele;
    @JsonProperty("Tumor_Seq_Allele1")
    private String tumorSeqAllele1;
    @JsonProperty("Tumor_Seq_Allele2")
    private String tumorSeqAllele2;
    @JsonProperty("dbSNP_RS")
    private String dbSnpRs;
    @JsonProperty("dbSNP_Val_Status")
    private String dbSnpValStatus;
    @JsonProperty("Tumor_Sample_Barcode")
    private String tumorSampleBarcode;
    @JsonProperty("Matched_Norm_Sample_Barcode")
    private String matchedNormSampleBarcode;
    @JsonProperty("Match_Norm_Seq_Allele1")
    private String matchNormSeqAllele1;
    @JsonProperty("Match_Norm_Seq_Allele2")
    private String matchNormSeqAllele2;
    @JsonProperty("Tumor_Validation_Allele1")
    private String tumorValidationAllele1;
    @JsonProperty("Tumor_Validation_Allele2")
    private String tumorValidationAllele2;
    @JsonProperty("Match_Norm_Validation_Allele1")
    private String matchNormValidationAllele1;
    @JsonProperty("Match_Norm_Validation_Allele2")
    private String matchNormValidationAllele2;
    @JsonProperty("Verification_Status")
    private String verificationStatus;
    @JsonProperty("Validation_Status")
    private String validationStatus;
    @JsonProperty("Mutation_Status")
    private String mutationStatus;
    @JsonProperty("Sequencing_Phase")
    private String sequencingPhase;
    @JsonProperty("Sequencing_Source")
    private String sequencingSource;
    @JsonProperty("Validation_Method")
    private String validationMethod;
    @JsonProperty("Score")
    private String score;
    @JsonProperty("BAM_File")
    private String bamFile;
    @JsonProperty("Sequencer")
    private String sequencer;
    @JsonProperty("HGVSp_Short")
    private String hgvspShort;
    @JsonProperty("TxChange")
    private String txChange;
    @JsonProperty("Transcript_Id")
    private String transcriptId;
    @JsonProperty("ENSEMBL_Gene_Id")
    private String ensemblGeneId;
    @JsonProperty("t_ref_count")
    private Integer tRefCount;
    @JsonProperty("t_alt_count")
    private Integer tAltCount;
    @JsonProperty("n_ref_count")
    private Integer nRefCount;
    @JsonProperty("n_alt_count")
    private Integer nAltCount;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getTumorSampleBarcode() {
        return this.tumorSampleBarcode;
    }

    public void setTumorSampleBarcode(String tumorSampleBarcode) {
        this.tumorSampleBarcode = tumorSampleBarcode.replace("_TD", "");
    }

    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

    public Integer getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Integer endPosition) {
        this.endPosition = endPosition;
    }

    public String getTxChange() {
        return txChange;
    }

    public String getHgvspShort() {
        return hgvspShort;
    }

    public String getHugoSymbol() {
        return hugoSymbol;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * Accepts only prefixed entries.
     * @param name Column name
     * @param value Column value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        if (!name.contains(".")) {
            return;
        }
        this.additionalProperties.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperty() {
        return this.additionalProperties;
    }

    @JsonIgnore
    public Collection<String> getAdditionalProperties() {
        return this.additionalProperties.keySet();
    }

    /**
     * Method to merge two lists of mutation entries.
     * @param maf1 Input 1
     * @param maf2 Input 2
     * @return Merged list of mutations
     */
    public static List<Maf> merge(Collection<Maf> maf1, Collection<Maf> maf2) {
        return new ArrayList<>(Stream.of(maf1, maf2).flatMap(Collection::stream).collect(Collectors.toMap(m -> {
            String s = m.getTumorSampleBarcode() + ";";
            s += m.getStartPosition() + ";";
            s += m.getEndPosition() + ";";
            s += m.getTxChange();
            return s;
        }, Function.identity(), (Maf x, Maf y) -> y)).values());
    }

}
