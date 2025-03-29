package ca.mcgill.ecse428.postr.dto;

public class LikeDTO {
    private Long id;
    private Long userId;
    private Long posterId;
    private Long numLikes;
    private String posterTitle;


    public LikeDTO() {
    }
    public LikeDTO(Long id, String posterName, Long userId, Long posterId, Long numLikes) {
        this.id = id;
        this.userId = userId;
        this.posterId = posterId;
        this.numLikes = numLikes;
        this.posterTitle = posterName;
    }


    public Long getId() {
        return id;
    }
    
    public String getPosterTitle() {
        return posterTitle;
    }

    public void setPosterTitle(String posterTitle) {
        this.posterTitle = posterTitle;
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

    public Long getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(Long numLikes) {
        this.numLikes = numLikes;
    }

    public void setPosterId(Long posterId) {
        this.posterId = posterId;
    }
}