"use client";

import { useState } from "react";
import TeamSection from "../TeamSection/TeamSection";
import TeamForm from "../TeamForm/TeamForm";
import styles from "./TeamFeed.module.css";

export default function TeamFeed({ teams }) {
  const [isAdding, setIsAdding] = useState(false);

  return (
    <>
      <div>
        {teams.map((team) => (
          <TeamSection key={team.id} team={team} />
        ))}
      </div>
      {teams.length === 0 && (
        <p className="noEntityInfo">Noch keine Teams vorhanden.</p>
      )}
      {!isAdding ? (
        <div
          className={`${styles.teamTitleCard} ${styles.addTeamSection}`}
          onClick={() => setIsAdding(true)}
        >
          <span className="material-symbols-outlined">add</span>
          <h4>Neues Team anlegen</h4>
        </div>
      ) : (
        <div className={styles.teamTitleCard}>
          <TeamForm
            onSuccess={() => setIsAdding(false)}
            onCancel={() => setIsAdding(false)}
          />
        </div>
      )}
    </>
  );
}
