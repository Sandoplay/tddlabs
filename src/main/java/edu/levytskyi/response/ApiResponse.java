package edu.levytskyi.response;

/*
 @author Sandoplay
 @project lab1
 @class ApiResponse
 @version 1.0.0
 @since 15.04.2026 - 19.10
*/

import java.util.List;

public class ApiResponse<M, D> {
  private M meta;
  private List<D> data;

  public ApiResponse(M meta, List<D> data) {
    this.meta = meta;
    this.data = data;
  }

  public M getMeta() { return meta; }
  public List<D> getData() { return data; }
}