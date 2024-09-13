import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Board } from '../interfaces/board.interface';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  apiUrl: string = "http://localhost:8080/api/boards";

  constructor(private http: HttpClient) { }

  getBoards(username: string, access_token: string): Observable<Board[]> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.get<Board[]>(`${this.apiUrl}/${username}`, {headers: headers}).pipe(map(this.processResponseArray.bind(this)));
  }

  getBoard(username: string, id: number, access_token: string): Observable<Board> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.get<Board>(`${this.apiUrl}/${username}/${id}`, {headers: headers}).pipe(map(this.processResponse));
  }

  addBoard(username: string, board: any, access_token: string): Observable<Board> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.post<Board>(`${this.apiUrl}/${username}`, board, {headers: headers});
  }

  deleteBoard(username: string, boardId: number, access_token: string): Observable<Board> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + access_token
    });
    return this.http.delete<Board>(`${this.apiUrl}/${username}/${boardId}`, {headers: headers});
  }

  private processResponse(response: Board): Board {
    return {
      id: response.id,
      name: response.name,
      description: response.description,
      lists: response.lists ? [...response.lists] : []
    };
  }

  private processResponseArray(response: Board[]): Board[] {
    return response.map(this.processResponse.bind(this));
  }

}




