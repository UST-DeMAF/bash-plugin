package ust.tad.bashplugin.analysistask;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalysisTaskResumeRequest {
    
    @JsonProperty("taskId")
    private UUID taskId;


    public AnalysisTaskResumeRequest() {
    }

    public AnalysisTaskResumeRequest(UUID taskId) {
        this.taskId = taskId;
    }

    public UUID getTaskId() {
        return this.taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public AnalysisTaskResumeRequest taskId(UUID taskId) {
        setTaskId(taskId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AnalysisTaskResumeRequest)) {
            return false;
        }
        AnalysisTaskResumeRequest analysisTaskResumeRequest = (AnalysisTaskResumeRequest) o;
        return Objects.equals(taskId, analysisTaskResumeRequest.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId);
    }

    @Override
    public String toString() {
        return "{" +
            " taskId='" + getTaskId() + "'" +
            "}";
    }


}
