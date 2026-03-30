"use client";
import styles from "./page.module.css";
import { useState, useEffect } from "react";
import api from "@/services/api";
import EditableNameField from "@/components/EditableNameField/EditableNameField";
import TraineeCard from "@/components/TraineeCard/TraineeCard";

// TODO: Could be centralized
function extractError(error) {
  if (error.fieldErrors?.length) return error.fieldErrors[0].error;
  return error.message || "Ein Fehler ist aufgetreten";
}

export default function Teams() {
  const [teams, setTeams] = useState([]);
  const [isAddingTeam, setIsAddingTeam] = useState(false);
  const [editingId, setEditingId] = useState(null);

  // Load teams
  useEffect(() => {
    const loadTeams = async () => {
      const { data, error } = await api.get("/teams");
      if (error) return;
      setTeams(data);
      console.log(data);
    };
    loadTeams();
  }, []);

  // Gibt null bei Erfolg, Error-String bei Fehler
  const handleCreate = async (name) => {
    const { data, error } = await api.post("/teams", { name });
    if (error) return extractError(error);
    setTeams([...teams, data]);
    setIsAddingTeam(false);
    return null;
  };

  // Gibt null bei Erfolg, Error-String bei Fehler

  const handleDelete = async (id) => {
    const { error } = await api.delete(`/teams/${id}`);
    if (error) return;
    setTeams(teams.filter((t) => t.id !== id));
  };

  return (
    <div>
      <h1>Teams & Lernende</h1>

      {teams.map((team) => (
        <div key={team.id}>
          {/* ===== Team Name Card ===== */}
          <div className={styles.teamTitleCard}>
            {editingId === team.id ? (
              /* ===== When editing team name ===== */
              <EditableNameField
                initialValue={team.name}
                onSave={(name) => handleUpdate(team.id, name)}
                onCancel={() => setEditingId(null)}
              />
            ) : (
              <>
                {/* ===== When NOT editing team name ===== */}
                <h4>{team.name}</h4>
                <div className={styles.crudButtons}>
                  <span
                    className="material-symbols-outlined"
                    onClick={() => setEditingId(team.id)}
                  >
                    edit
                  </span>
                  <span
                    className="material-symbols-outlined"
                    onClick={() => handleDelete(team.id)}
                  >
                    delete
                  </span>
                </div>
              </>
            )}
          </div>
          {/* ===== Show a card for each trainee on this team ===== */}
          <div className={styles.traineeCards}>
            {team.trainees.map((trainee) => (
              <TraineeCard key={trainee.id} trainee={trainee} />
            ))}
            <div className={styles.traineeCards}>add trainee</div>
          </div>
        </div>
      ))}

      {isAddingTeam ? (
        <div className={`${styles.teamTitleCard} ${styles.addTeamSection}`}>
          <EditableNameField
            onSave={handleCreate}
            onCancel={() => setIsAddingTeam(false)}
            minLength={2}
            maxLength={30}
            placeholder="Teamname"
          />
        </div>
      ) : (
        <div
          className={`${styles.teamTitleCard} ${styles.addTeamSection}`}
          onClick={() => setIsAddingTeam(true)}
        >
          <span className="material-symbols-outlined">add</span>
          <h4>Neues Team anlegen</h4>
        </div>
      )}
    </div>
  );
}
