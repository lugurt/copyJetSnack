package com.example.copy

import com.google.gson.annotations.SerializedName

data class ApkConfig(
  @SerializedName("arch") val arch: String,
  @SerializedName("branch_name") val branch_name: String,
  @SerializedName("build_type") val build_type: String,
  @SerializedName("commit_id") val commit_id: String,
  @SerializedName("gradle_tasks") val gradle_tasks: String,
  @SerializedName("origin_task_id") val origin_task_id: String,
  @SerializedName("task_id") val task_id: String
)