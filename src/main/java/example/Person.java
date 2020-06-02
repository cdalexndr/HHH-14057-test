package example;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Nickname> nicknames = new HashSet<>();

  public Person() {
  }

  public Person(String name) {
    this.name = name;
  }

  public void addAddress(Nickname nickname) {
    nicknames.add(nickname);
    nickname.setPerson(this);
  }

  public Set<Nickname> getNicknames() {
    return nicknames;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Person)) {
      return false;
    }
    Person person = (Person) o;
    return getName().equals(person.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}
