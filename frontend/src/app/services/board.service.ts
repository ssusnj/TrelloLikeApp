import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  apiUrl: string = "http://localhost:8080/api/boards";

  constructor(private http: HttpClient) { }

  getBoards(): Observable<BoardResponse[]> {
    return this.http.get<BoardResponse[]>(`${this.apiUrl}`);
      /* pipe().map(b => {
        return this.processResponse(b);
      }) */    
  }

  getBoard(id: number = 1): Observable<BoardResponse> {
    return this.http.get<BoardResponse>(`${this.apiUrl}/${id}`);
  }

  /* private processResponse(response: any[]): BoardResponse[] {
    return response.map(item => ({
      name: item.name,
      createdBy: item.createdBy,
      description: item.description,
      id: item.id
      //lists: { ...response.list}
    }));
  }
 */
}

export interface BoardResponse {
  createdBy: string;
  description: string
  id: string;
  name: string;
}
