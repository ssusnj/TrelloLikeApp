import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { List } from '../interfaces/list.interface';
import { Card } from '../interfaces/card.interface';

@Injectable({
  providedIn: 'root'
})
export class ListService {

  apiUrl: string = "http://localhost:8080/api/boards";

  constructor(private http: HttpClient) { }

  addList(username: string, boardId: number, list: List, access_token: string): Observable<List> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.post<List>(`${this.apiUrl}/${username}/${boardId}`, list, {headers: headers}); 
  }

  deleteList(username: string, boardId: number, listId: number, access_token: string): Observable<List> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.delete<List>(`${this.apiUrl}/${username}/${boardId}/${listId}`, {headers: headers});
  }

  addCard(username: string, boardId: number, listId: number, cardTitle: string,  access_token: string): Observable<Card> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.post<Card>(`${this.apiUrl}/${username}/${boardId}/${listId}`, cardTitle, {headers: headers});
  }

  updateCard(username: string, boardId: number, listId: number, card: Card,  access_token: string): Observable<Card> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.put<Card>(`${this.apiUrl}/${username}/${boardId}/${listId}/${card.id}`, card, {headers: headers});
  }
  
  deleteCard(username: string, boardId: number, listId: number, card: Card,  access_token: string): Observable<Card> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.delete<Card>(`${this.apiUrl}/${username}/${boardId}/${listId}/${card.id}`, {headers: headers});
  }
}
