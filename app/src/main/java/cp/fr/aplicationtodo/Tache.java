package cp.fr.aplicationtodo;

/**
 * Created by Formation on 11/01/2018.
 */

public class Tache {

    private Long id;
    private String listtache;
    private Integer afaire;
    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListtache() {
        return listtache;
    }

    public void setListtache(String listtache) {
        this.listtache = listtache;
    }

    public Integer getAfaire() {
        return afaire;
    }

    public void setAfaire(Integer afaire) {
        this.afaire = afaire;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Tache() {
    }

    public Tache(String listtache, Integer afaire, String user) {
        this.listtache = listtache;
        this.afaire = afaire;
        this.user = user;
    }
}
