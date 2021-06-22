import { NPC } from "./npcType"

export type loginResponse = {
    successfull: boolean,
    session: string;
}

export type verifyResponse = {
    verified: boolean
}

export type getAllNPCResponse = {
    npc:NPC[]
}