import { Component, OnInit } from '@angular/core';
import { Apollo, Mutation } from 'apollo-angular';
import { Observable } from 'rxjs';
import { map, subscribeOn } from 'rxjs/operators';
import gql from 'graphql-tag';
import { User, Query, Mutation1 } from '../type'


@Component({
  selector: 'app-operations',
  templateUrl: './operations.component.html',
  styleUrls: ['./operations.component.css']
})
export class OperationsComponent implements OnInit {

  getUsers: Observable<User[]>;
  showNew: boolean = false;
  editUser: boolean = false;
  id: string;
  constructor(private apollo: Apollo) {
  }
  getDetails() {
    this.displayUsers();
  }
  ngOnInit() {
    this.getDetails()
    {
      this.displayUsers();
    }

  }

  displayUsers() {
    this.getUsers = this.apollo.watchQuery<Query>({
      query: gql`
        query getUsers {
          getUsers {
            id
            userName
            email
          }
        }
        
      `
    })
      .valueChanges
      .pipe(
        map(result => result.data.getUsers)
      );
  }

  onDelete(id) {
    const deleteUserById = gql`
    mutation deleteUserById($id:ID!) {
      deleteUserById(id:$id) {
        id
      }
    }
  `;
    const userId = id;
    console.log(userId);
    this.apollo.mutate({
      mutation: deleteUserById,
      variables: {
        id: userId
      }
    }).
      subscribe(({ data }) => {
        console.log('got data', data);


      },
        (error) => {
          console.log('there was an error sending the query', error);

        });
    //this.getDetails();
  }

  onNew() {
    this.showNew = true;
  }

  addUser(username, email) {
    console.log(username, email);
    const addNewUser = gql`
        mutation addUser($userName:String!,$email:String!){
              addUser(userName:$userName,email:$email) {
         userName
          email
        } 
      } `;
    const user_name = username;
    const e_mail = email;
    this.apollo.mutate(
      {
        mutation: addNewUser,
        variables:
        {
          userName: user_name,
          email: e_mail
        }
      }
    ).subscribe(
      ({ data }) => {
        console.log(data)
        this.displayUsers();
      }
      , (error) => {
        console.log('there was an error sending the query', error);
      }
    )
  }

  onEdit(id) {
    this.editUser = true;
    this.id = id;
  }

  updateUserByName(name) {

    const updateUserByUserName = gql`
        mutation updateUser($id:ID!,$userName:String!)
          {
            updateUser(id:$id,userName:$userName)
            {
                userName
                email 
            }
          }
          `;

    const userId = this.id;
    console.log(userId);
    const user_name = name;
    console.log(name);
    this.apollo.mutate({
      mutation: updateUserByUserName,
      variables:
      {
        id: userId,
        userName: user_name
      }
    }).subscribe(
      ({ data }) => {
        console.log(data)
        this.displayUsers();
      }
      , (error) => {
        console.log('there was an error in sending the query', error);
      }
    )
  }



}
