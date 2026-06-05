"use client";

import { useEffect, useMemo, useRef, useState } from "react";
import styles from "./MonthAssignmentsBoard.module.css";
import { getSchoolYearMonths } from "@/lib/dateUtils/WorkdaysCalendarUtils";
import MonthAssignmentsTeamSwitch from "../MonthAssignmentsTeamSwitch/MonthAssignmentsTeamSwitch";

const LABEL_COLUMN_WIDTH_REM = 10;
const MONTH_COLUMN_WIDTH_REM = 10;
const COLUMN_GAP_REM = 0.5;

function chunkArray(items, chunkSize) {
  const chunks = [];

  for (let index = 0; index < items.length; index += chunkSize) {
    chunks.push(items.slice(index, index + chunkSize));
  }

  return chunks;
}

function getMonthsPerBlock(containerWidthPx, rootFontSizePx) {
  if (!containerWidthPx || !rootFontSizePx) {
    return 12;
  }

  const labelWidthPx = LABEL_COLUMN_WIDTH_REM * rootFontSizePx;
  const monthWidthPx = MONTH_COLUMN_WIDTH_REM * rootFontSizePx;
  const gapPx = COLUMN_GAP_REM * rootFontSizePx;
  const usableWidth = containerWidthPx;

  const rawMonths =
    (usableWidth - labelWidthPx + gapPx) / (monthWidthPx + gapPx);

  return Math.max(1, Math.min(12, Math.floor(rawMonths)));
}

export default function MonthAssignmentsBoard({
  schoolYearStartYear,
  teams,
  choreCategories,
}) {
  const boardRef = useRef(null);
  const [containerWidth, setContainerWidth] = useState(0);
  const [rootFontSize, setRootFontSize] = useState(16);

  useEffect(() => {
    const rootSize = Number.parseFloat(
      window.getComputedStyle(document.documentElement).fontSize,
    );
    setRootFontSize(Number.isFinite(rootSize) ? rootSize : 16);

    if (!boardRef.current || typeof ResizeObserver === "undefined") {
      return undefined;
    }

    const observer = new ResizeObserver((entries) => {
      const entry = entries[0];

      if (entry?.contentRect?.width) {
        setContainerWidth(entry.contentRect.width);
      }
    });

    observer.observe(boardRef.current);

    return () => observer.disconnect();
  }, []);

  const months = useMemo(
    () => getSchoolYearMonths(schoolYearStartYear),
    [schoolYearStartYear],
  );

  const monthsPerBlock = getMonthsPerBlock(containerWidth, rootFontSize);
  const monthBlocks = useMemo(
    () => chunkArray(months, monthsPerBlock),
    [months, monthsPerBlock],
  );

  return (
    <div ref={boardRef} className={styles.board}>
      {monthBlocks.map((monthBlock, blockIndex) => (
        <section key={blockIndex} className={styles.block}>
          <div
            className={styles.row}
            style={{
              gridTemplateColumns: `minmax(${LABEL_COLUMN_WIDTH_REM}rem, ${LABEL_COLUMN_WIDTH_REM}rem) repeat(${monthBlock.length}, minmax(${MONTH_COLUMN_WIDTH_REM}rem, 1fr))`,
            }}
          >
            <div
              className={`${styles.cell} ${styles.labelCell} ${styles.headerCell}`}
            ></div>
            {monthBlock.map(({ monthName, year, monthIndex }) => (
              <div
                key={`${year}-${monthIndex}`}
                className={`${styles.cell} ${styles.monthCell} ${styles.headerCell}`}
              >
                <span>{monthName}</span>
              </div>
            ))}
          </div>

          {choreCategories.map((choreCategory) => (
            <div
              key={`${blockIndex}-${choreCategory.id}`}
              className={styles.row}
              style={{
                gridTemplateColumns: `minmax(${LABEL_COLUMN_WIDTH_REM}rem, ${LABEL_COLUMN_WIDTH_REM}rem) repeat(${monthBlock.length}, minmax(${MONTH_COLUMN_WIDTH_REM}rem, 1fr))`,
              }}
            >
              <div className={`${styles.cell} ${styles.labelCell}`}>
                {choreCategory.name}
              </div>
              {monthBlock.map(({ monthName, year, monthIndex }) => (
                <div
                  key={`${choreCategory.id}-${year}-${monthIndex}`}
                  className={`${styles.cell} ${styles.monthCell}`}
                >
                  <MonthAssignmentsTeamSwitch
                    teams={teams}
                    monthName={monthName}
                  />
                </div>
              ))}
            </div>
          ))}
        </section>
      ))}

      {choreCategories.length === 0 && (
        <p className="noEntityInfo">Noch keine Chore Categories vorhanden.</p>
      )}
    </div>
  );
}
