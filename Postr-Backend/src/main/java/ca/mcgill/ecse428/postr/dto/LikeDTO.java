package ca.mcgill.ecse428.postr.dto;

public class LikeDTO {
    private Long id;
    private Long userId;
    private Long posterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPosterId() {
        return posterId;
    }

    public void setPosterId(Long posterId) {
        this.posterId = posterId;
    }
}