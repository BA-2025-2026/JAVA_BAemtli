"use server";

import TeamsAPI from "@/lib/api/Teams";
import { revalidatePath } from "next/cache";

export async function deleteTeamAction(id) {
  // verify session first?
  // Delete Team (hand in JWT Token later)
  await TeamsAPI.delete(id);
  revalidatePath("/teams");
}
