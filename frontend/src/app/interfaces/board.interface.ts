import { List } from "./list.interface";

export interface Board {
    id: number;
    name: string;
    description: string;
    lists: List[];
  }