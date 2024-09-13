import { Card } from "./card.interface";

export interface List {
    id: number;
    name: string;
    cards: Card[];
  }
