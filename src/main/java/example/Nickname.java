package example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Nickname {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String address;
  private boolean current;

  @ManyToOne
  private Person person;

  protected Nickname() {
  }

  public Nickname(String address) {
    this.address = address;
  }

  public int getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Person getPerson() {
    return person;
  }

  public void setCurrent(boolean current) {
    this.current = current;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Nickname address1 = (Nickname) o;
    return getAddress().equals(address1.getAddress()) &&
        getPerson().equals(address1.getPerson());
  }

  @Override
  public int hashCode() {
    assert getAddress() != null; //fails
    return getAddress().hashCode();
  }
}
