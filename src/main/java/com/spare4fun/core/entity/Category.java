package flag_camp_2020_t3.com.spare4fun.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/** Represents a category
 * @author Qiuyu(Joanna) Deng
 * @author www.spare4fun.com
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name="category")
public class Category implements Serializable {
    private static final long serialVersionUID = 23432L;

    @Id
    @Column(name="category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Column(name="category")
    @Getter
    @Setter
    String category;

}