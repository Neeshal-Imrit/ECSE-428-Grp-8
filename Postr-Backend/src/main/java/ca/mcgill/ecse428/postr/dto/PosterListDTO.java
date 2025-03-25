package ca.mcgill.ecse428.postr.dto;

import java.util.List;

public class PosterListDTO {
    private List<PosterResponseDTO> posters;

    public PosterListDTO(List<PosterResponseDTO> posters) {
        this.posters = posters;
    }

    public List<PosterResponseDTO> getPosters() {
        return posters;
    }

    public void setPosterList(List <PosterResponseDTO> posters) {
        this.posters = posters;
    }
}

