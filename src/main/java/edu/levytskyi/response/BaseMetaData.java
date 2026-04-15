package edu.levytskyi.response;

/*
 @author Sandoplay
 @project lab1
 @class BaseMetaData
 @version 1.0.0
 @since 15.04.2026 - 19.09
*/

public class BaseMetaData {
  private boolean success;
  private int code;
  private String errorMessage;

  public BaseMetaData(boolean success, int code, String errorMessage) {
    this.success = success;
    this.code = code;
    this.errorMessage = errorMessage;
  }

  public boolean isSuccess() { return success; }
  public int getCode() { return code; }
  public String getErrorMessage() { return errorMessage; }
}