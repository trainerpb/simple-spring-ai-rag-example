package home.soham.tools.docs.ai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * -- public.tbl_insurance definition
 * <p>
 * -- Drop table
 * <p>
 * -- DROP TABLE public.tbl_insurance;
 * <p>
 * CREATE TABLE public.tbl_insurance (
 * policy_no varchar NOT NULL,
 * insured varchar NOT NULL,
 * company varchar NOT NULL,
 * start_date date NULL,
 * end_date varchar NOT NULL,
 * sa int4 NOT NULL,
 * premium_date varchar NOT NULL,
 * premium_amount int4 NOT NULL,
 * contact varchar NOT NULL,
 * CONSTRAINT insurance_tbl_unique UNIQUE (policy_no)
 * );
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_insurance")
public class Insurance {

    @Id
    String policy_no;
    String insured;
    String company;
    LocalDate start_date;
    LocalDate end_date;
    int sa;
    String premium_date;
    int premium_amount;
    String contact;

    @Override
    public String toString() {
        return "Insurace{" +
                "policy_no='" + policy_no + '\'' +
                ", insured='" + insured + '\'' +
                ", company='" + company + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", sa=" + sa +
                ", premium_date='" + premium_date + '\'' +
                ", premium_amount=" + premium_amount +
                ", contact='" + contact + '\'' +
                '}';
    }
}
