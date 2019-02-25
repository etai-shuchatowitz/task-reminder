package sendreminder.model;

public class Task {

  private String taskName;
  private String assignee;
  private boolean done;
  private int taskNumber;

  public Task(String taskName, String assignee, int taskNumber, boolean done) {
    this.taskName = taskName;
    this.assignee = assignee;
    this.taskNumber = taskNumber;
    this.done = done;
  }

  public int getTaskNumber() {
    return taskNumber;
  }

  public void setTaskNumber(int taskNumber) {
    this.taskNumber = taskNumber;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  @Override
  public String toString() {
    return "Task{"
        + "taskName='"
        + taskName
        + '\''
        + ", assignee='"
        + assignee
        + '\''
        + ", done="
        + done
        + ", taskNumber="
        + taskNumber
        + '}';
  }
}
