package edu.levytskyi.model;

/*
 @author Sandoplay
 @project lab1
 @class Item
 @version 1.0.0
 @since 15.04.2026 - 16.28
*/

public class Item {
  private Long id;
  private String name;
  private String code;
  private String description;

  public Item(Long id, String name, String code, String description) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.description = description;
  }

  // Гетери та сетери
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}