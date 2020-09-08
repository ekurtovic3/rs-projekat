package ba.unsa.etf.rs.model;

public class Country
{
    //ATTRIBUTES
    private String countryName;
    private String countryAbbreviation2Letters;
    private String countryAbbreviation3Letters;
    private Integer countryNumcode;
    private Integer countryPhonecode;

    //GETTERS AND SETTERS
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryAbbreviation2Letters() {
        return countryAbbreviation2Letters;
    }

    public void setCountryAbbreviation2Letters(String countryAbbreviation2Letters) {
        this.countryAbbreviation2Letters = countryAbbreviation2Letters;
    }

    public String getCountryAbbreviation3Letters() {
        return countryAbbreviation3Letters;
    }

    public void setCountryAbbreviation3Letters(String countryAbbreviation3Letters) {
        this.countryAbbreviation3Letters = countryAbbreviation3Letters;
    }

    public Integer getCountryNumcode() {
        return countryNumcode;
    }

    public void setCountryNumcode(Integer countryNumcode) {
        this.countryNumcode = countryNumcode;
    }

    public Integer getCountryPhonecode() {
        return countryPhonecode;
    }

    public void setCountryPhonecode(Integer countryPhonecode) {
        this.countryPhonecode = countryPhonecode;
    }

    //CONSTRUCTOR
    public Country()
    {}

    public Country(String countryName, String countryAbbreviation2Letters, String countryAbbreviation3Letters,
                   Integer countryNumcode, Integer countryPhonecode)
    {
        this.countryName = countryName;
        this.countryAbbreviation2Letters = countryAbbreviation2Letters;
        this.countryAbbreviation3Letters = countryAbbreviation3Letters;
        this.countryNumcode = countryNumcode;
        this.countryPhonecode = countryPhonecode;
    }

    public Country(String countryName, String countryAbbreviation2Letters, String countryAbbreviation3Letters)
    {
        this.countryName = countryName;
        this.countryAbbreviation2Letters = countryAbbreviation2Letters;
        this.countryAbbreviation3Letters = countryAbbreviation3Letters;
    }

    //METHODS
    @Override
    public String toString()
    {
        return countryName ;
    }
}
