// Request.java
public class Request {
    String id;
    String requester;
    String contact;
    String category;
    String technician;
    String priority;
    String status;
    String remarks;

    public Request(String id, String requester, String contact, String category,
                   String technician, String priority, String status, String remarks) {
        this.id = id;
        this.requester = requester;
        this.contact = contact;
        this.category = category;
        this.technician = technician;
        this.priority = priority;
        this.status = status;
        this.remarks = remarks;
    }
}
