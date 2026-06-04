import styles from "./WorkdayMonth.module.css";
import { WEEKDAYS, getMonthWeeks } from "@/lib/dateUtils/WorkdaysCalendarUtils";

export default function WorkdayMonth({ year, monthIndex, monthName }) {
  const weeks = getMonthWeeks(year, monthIndex);

  return (
    <section className={styles.month}>
      <h3 className={styles.monthTitle}>{monthName}</h3>
      <div className={styles.calendarGrid}>
        <div className={styles.headerRow}>
          <div className={`${styles.cell} ${styles.headerCell}`}>KW</div>
          {WEEKDAYS.map((weekday) => (
            <div
              key={weekday}
              className={`${styles.cell} ${styles.headerCell}`}
            >
              {weekday}
            </div>
          ))}
        </div>

        {weeks.map((week) => (
          <div
            key={`${year}-${monthIndex}-${week.kw}`}
            className={styles.weekRow}
          >
            <div className={`${styles.cell} ${styles.kwCell}`}>{week.kw}</div>
            {week.days.map((day, index) => (
              <div
                key={`${week.kw}-${index}`}
                className={`${styles.cell} ${day ? styles.dayCell : styles.emptyCell}`}
                aria-hidden={!day}
              >
                {day ? String(day.getUTCDate()).padStart(2, "0") : ""}
              </div>
            ))}
          </div>
        ))}
      </div>
    </section>
  );
}
