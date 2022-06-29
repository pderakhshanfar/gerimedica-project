package nl.gerimedica.model;


import org.apache.commons.csv.CSVRecord;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // No need to pass id, it will be auto generated
    private Long id;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "codeListCode", nullable = false)
    private String codeListCode;

    @Column(name = "code", nullable = false, unique = true) // As mentioned in the requirements, the code should be unique
    private String code;

    @Column(name = "displayValue", nullable = false)
    private String displayValue;

    @Column(name = "longDescription") // Some entries have empty longDescription
    private String longDescription;

    @Column(name = "fromDate", nullable = false)
    private LocalDate fromDate;

    @Column(name = "toDate") // Some entries have empty toDate
    private LocalDate toDate;

    @Column(name = "sorting_priority") // Some entries have empty sortingPriority
    private Integer sortingPriority;

    //default constructor
    public Record(){}

    // Constructor to map records of uploaded CSV file to an Record object
    public Record(CSVRecord record) {
        DateTimeFormatter europeanDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        this.source = record.get("source");
        this.codeListCode = record.get("codeListCode");
        this.code = record.get("code");
        this.displayValue = record.get("displayValue");
        this.longDescription = record.get("longDescription");
        this.fromDate = LocalDate.parse(record.get("fromDate"), europeanDateFormat);

        try{
            this.toDate = LocalDate.parse(record.get("toDate"), europeanDateFormat);
        }catch(Exception e){
            // do nothing. keep the value null
        }

        try{
            this.sortingPriority = Integer.valueOf(record.get("sortingPriority"));
        }catch(Exception e){
            // do nothing. keep the value null
        }

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCodeListCode() {
        return codeListCode;
    }

    public void setCodeListCode(String codeListCode) {
        this.codeListCode = codeListCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getSortingPriority() {
        return sortingPriority;
    }

    public void setSortingPriority(Integer sortingPriority) {
        this.sortingPriority = sortingPriority;
    }
}
