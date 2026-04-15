package edu.levytskyi.response;

/*
 @author Sandoplay
 @project lab1
 @class PaginationMetaData
 @version 1.0.0
 @since 15.04.2026 - 19.50
*/


public class PaginationMetaData {
  private int number;
  private int size;
  private long totalElements;
  private int totalPages;
  private boolean isFirst;
  private boolean isLast;

  public PaginationMetaData(int number, int size, long totalElements) {
    this.number = number;
    this.size = size;
    this.totalElements = totalElements;
    this.totalPages = (int) Math.ceil((double) totalElements / size);
    this.isFirst = (number == 0);
    this.isLast = (number >= totalPages - 1);
  }
  public int getNumber() { return number; }
  public long getTotalElements() { return totalElements; }
  public int getTotalPages() { return totalPages; }
  public boolean isFirst() { return isFirst; }
  public boolean isLast() { return isLast; }
}