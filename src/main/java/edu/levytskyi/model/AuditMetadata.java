package edu.levytskyi.model;

/*
 @author Sandoplay
 @project lab1
 @class AuditMetadata
 @version 1.0.0
 @since 15.04.2026 - 19.09
*/

import java.time.LocalDateTime;

public abstract class AuditMetadata {
  private LocalDateTime createdDate;
  private String createdBy;

  public LocalDateTime getCreatedDate() { return createdDate; }
  public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
  public String getCreatedBy() { return createdBy; }
  public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}