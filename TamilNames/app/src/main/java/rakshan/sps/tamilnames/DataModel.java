package rakshan.sps.tamilnames;

public class DataModel {

    private String Title;
    private String ImageUrl;
    private String alphabet;
    private String name;
    private String meaning;
    private String birthday_person;

    public String getBirthday_person() {
        return birthday_person;
    }

    public void setBirthday_person(String birthday_person) {
        this.birthday_person = birthday_person;
    }

    public String getBirthday_person_pic() {
        return birthday_person_pic;
    }

    public void setBirthday_person_pic(String birthday_person_pic) {
        this.birthday_person_pic = birthday_person_pic;
    }

    private String birthday_person_pic;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String sex;

    public DataModel() {
    }

    public DataModel(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imgUrl) {
        ImageUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }
}
