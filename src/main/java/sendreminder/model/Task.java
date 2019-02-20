package sendreminder.model;

public class Task {

  private String task;
  private String assignee;
  private boolean done;
  private int index;

  public Task(String task, String assignee, int index, boolean done) {
    this.task = task;
    this.assignee = assignee;
    this.index = index;
    this.done = done;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
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
        + "task='"
        + task
        + '\''
        + ", assignee='"
        + assignee
        + '\''
        + ", done="
        + done
        + ", index="
        + index
        + '}';
  }
}
