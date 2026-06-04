import styles from "./WorkdaysCalendar.module.css";

const MONTHS = [
  "August",
  "September",
  "Oktober",
  "November",
  "Dezember",
  "Januar",
  "Februar",
  "März",
  "April",
  "Mai",
  "Juni",
  "Juli",
];

const WEEKDAYS = ["Mo", "Di", "Mi", "Do", "Fr"];

function createUtcDate(year, monthIndex, day) {
  // Build dates in UTC at midday so timezone offsets do not shift the day.
  return new Date(Date.UTC(year, monthIndex, day, 12));
}

function getWeekdayIndex(date) {
  // JavaScript returns Sunday as 0, but this calendar needs Monday as 1.
  return date.getUTCDay() === 0 ? 7 : date.getUTCDay();
}

function getIsoWeekNumber(date) {
  // ISO week numbers are based on the Thursday of the current week.
  const target = createUtcDate(
    date.getUTCFullYear(),
    date.getUTCMonth(),
    date.getUTCDate(),
  );

  // Shift the date to the week anchor used by ISO week calculations.
  target.setUTCDate(target.getUTCDate() + 4 - getWeekdayIndex(target));

  const yearStart = new Date(Date.UTC(target.getUTCFullYear(), 0, 1));
  const diffInDays = Math.floor((target - yearStart) / 86400000) + 1;

  return Math.ceil(diffInDays / 7);
}

function getFirstWorkdayOfMonth(year, monthIndex) {
  // Find the first weekday in the month so the calendar starts on a workday row.
  for (let day = 1; day <= 7; day += 1) {
    const candidate = createUtcDate(year, monthIndex, day);
    const weekday = candidate.getUTCDay();

    if (weekday >= 1 && weekday <= 5) {
      return candidate;
    }
  }

  return createUtcDate(year, monthIndex, 1);
}

function getLastWorkdayOfMonth(year, monthIndex) {
  // Find the last weekday so the month grid does not render trailing weekend rows.
  const lastDay = new Date(Date.UTC(year, monthIndex + 1, 0, 12));

  for (
    let day = lastDay.getUTCDate();
    day >= lastDay.getUTCDate() - 6;
    day -= 1
  ) {
    const candidate = createUtcDate(year, monthIndex, day);
    const weekday = candidate.getUTCDay();

    if (weekday >= 1 && weekday <= 5) {
      return candidate;
    }
  }

  return lastDay;
}

function getMondayOfWeek(date) {
  // Each visible row starts with Monday, even if the month begins midweek.
  const monday = createUtcDate(
    date.getUTCFullYear(),
    date.getUTCMonth(),
    date.getUTCDate(),
  );
  const weekday = getWeekdayIndex(monday);
  monday.setUTCDate(monday.getUTCDate() - (weekday - 1));
  return monday;
}

function getMonthWeeks(year, monthIndex) {
  // Build week rows from the first Monday before the month to the last workday.
  const firstWorkday = getFirstWorkdayOfMonth(year, monthIndex);
  const lastWorkday = getLastWorkdayOfMonth(year, monthIndex);
  const startOfWeeks = getMondayOfWeek(firstWorkday);
  const endOfWeeks = createUtcDate(
    lastWorkday.getUTCFullYear(),
    lastWorkday.getUTCMonth(),
    lastWorkday.getUTCDate(),
  );

  const weeks = [];
  let cursor = startOfWeeks;

  while (cursor <= endOfWeeks) {
    // Keep only the weekday cells that belong to the current month.
    const days = WEEKDAYS.map((_, index) => {
      const dayDate = createUtcDate(
        cursor.getUTCFullYear(),
        cursor.getUTCMonth(),
        cursor.getUTCDate() + index,
      );

      return dayDate.getUTCMonth() === monthIndex ? dayDate : null;
    });

    weeks.push({
      kw: getIsoWeekNumber(cursor),
      days,
    });

    cursor = createUtcDate(
      cursor.getUTCFullYear(),
      cursor.getUTCMonth(),
      cursor.getUTCDate() + 7,
    );
  }

  return weeks;
}

function WorkdayMonth({ year, monthIndex, monthName }) {
  const weeks = getMonthWeeks(year, monthIndex);

  return (
    <section className={styles.month}>
      <h3 className={styles.monthTitle}>{monthName}</h3>
      <div className={styles.calendarGrid}>
        <div className={`${styles.cell} ${styles.headerCell}`}>KW</div>
        {WEEKDAYS.map((weekday) => (
          <div key={weekday} className={`${styles.cell} ${styles.headerCell}`}>
            {weekday}
          </div>
        ))}

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

export default function WorkdaysCalendar({ schoolYearStartYear }) {
  // The display order is already August to July, so the loop index is the month position.
  const months = MONTHS.map((monthName, index) => {
    // Convert the display position to the real JavaScript month index for date math.
    const monthIndex = (index + 7) % 12;

    return {
      monthName,
      monthIndex,
      // January to July belong to the next calendar year in the school year view.
      year: schoolYearStartYear + (monthIndex <= 6 ? 1 : 0),
    };
  });

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
