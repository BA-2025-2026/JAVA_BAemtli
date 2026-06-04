// Keep the page dynamic so the school year is always derived from the current date.
export const dynamic = "force-dynamic";

import WorkdaysCalendar from "@/components/WorkdaysCalendar/WorkdaysCalendar";
import styles from "./page.module.css";

function getZurichDateParts(date = new Date()) {
  const formatter = new Intl.DateTimeFormat("en-CA", {
    timeZone: "Europe/Zurich",
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  });

  const parts = formatter.formatToParts(date);
  const values = Object.fromEntries(
    parts
      .filter((part) => part.type !== "literal")
      .map((part) => [part.type, part.value]),
  );

  return {
    year: Number(values.year),
    month: Number(values.month),
    day: Number(values.day),
  };
}

function getSchoolYearStartYear(currentDate = new Date()) {
  const { year, month } = getZurichDateParts(currentDate);
  return month >= 8 ? year : year - 1;
}

export default function Workdays() {
  const schoolYearStartYear = getSchoolYearStartYear();
  const schoolYearEndYear = schoolYearStartYear + 1;

  return (
    <>
      <div className={styles.header}>
        <h1>Arbeitstage</h1>
        <p className={styles.subtitle}>
          Schuljahr {schoolYearStartYear} bis {schoolYearEndYear}
        </p>
      </div>
      <WorkdaysCalendar schoolYearStartYear={schoolYearStartYear} />
    </>
  );
}
