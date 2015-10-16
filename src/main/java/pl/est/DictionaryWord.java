package pl.est;

public class DictionaryWord {

    private String polishWord;
    private String englishWord;

    public DictionaryWord(String polishWord, String englishWord) {
        this.polishWord = polishWord;
        this.englishWord = englishWord;
    }

    public String getPolishWord() {
        return polishWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DictionaryWord{");
        sb.append("polishWord='").append(polishWord).append('\'');
        sb.append(", englishWord='").append(englishWord).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
