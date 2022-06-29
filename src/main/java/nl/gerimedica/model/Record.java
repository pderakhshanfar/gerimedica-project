package nl.gerimedica.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

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
