// Prevent Container Image build from trying to pre-render this route statically without access to data
export const dynamic = "force-dynamic";

import TeamWrapper from "@/components/TeamWrapper/TeamWrapper";

export default function Teams() {
  return (
    <>
      <h1>Teams & Lernende</h1>
      <TeamWrapper />
    </>
  );
}
