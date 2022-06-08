package jandy3.DesignSeller.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class RequestFile {
    @Id
    @GeneratedValue
    @Column(name = "request_file_id")
    private Long id;

    @Setter
    private String filename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    @JsonIgnore
    @Setter
    private Request request;

    //== 생성 메서드 ==//
    public static RequestFile createRequestFile(String filename) {
        RequestFile requestFile = new RequestFile();
        requestFile.setFilename(filename);
        return requestFile;
    }

}
