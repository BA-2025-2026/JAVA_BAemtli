import styles from "./SchoolYearCalendar.module.css";
import { getSchoolYearMonths } from "@/lib/dateUtils/workdaysCalendar";
import WorkdayMonth from "../WorkdayMonth/WorkdayMonth";

export default function SchoolYearCalendar({ schoolYearStartYear }) {
  const months = getSchoolYearMonths(schoolYearStartYear);

  return (
    <div className={styles.calendar}>
      {months.map(({ year, monthIndex, monthName }) => (
        <WorkdayMonth
          key={`${year}-${monthIndex}`}
          year={year}
          monthIndex={monthIndex}
          monthName={monthName}
        />
      ))}
    </div>
  );
}
