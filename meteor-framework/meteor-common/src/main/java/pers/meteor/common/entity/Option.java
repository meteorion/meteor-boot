package pers.meteor.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 下拉选项对象
 *
 * @author haoxr
 * @since 2022/1/22
 */
@Data
@NoArgsConstructor
public class Option<T> {

    public Option(T value, String label) {
        this.value = value;
        this.label = label;
    }

    public Option(T value, String label, List<Option<T>> children) {
        this.value = value;
        this.label = label;
        this.children= children;
    }

    public Option(T value, String label, String tag) {
        this.value = value;
        this.label = label;
        this.tag= tag;
    }


    private T value;

    private String label;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String tag;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Option<T>> children;
}
