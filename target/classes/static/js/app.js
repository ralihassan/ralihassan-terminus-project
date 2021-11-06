var app = angular.module('app',[]);

app.controller('PersonController', ['$scope','PersonService', function ($scope,PersonService) {
    
    $scope.addPerson = function () {
        if ($scope.person != null && $scope.person.name && $scope.person.email) {
            PersonService.addPerson($scope.person.name, $scope.person.email, $scope.person.number)
              .then (function success(response){
                  $scope.message = 'Person added!';
                  $scope.errorMessage = '';
              },
              function error(response){
                  $scope.errorMessage = 'Error adding user!';
                  $scope.message = '';
            });
        }
        else {
            $scope.errorMessage = 'Please enter a name and email!';
            $scope.message = '';
        }
    }
    
    $scope.getAllPersons = function () {
        PersonService.getAllPersons()
          .then(function success(response){
              $scope.persons = response.data;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response ){
              $scope.message='';
              $scope.errorMessage = 'Error getting persons!';
          });
    }

}]);

app.service('PersonService',['$http', function ($http) {
	
    this.addPerson = function addUser(name, email, number){
        return $http({
          method: 'POST',
          url: 'person/save',
          data: {name:name, email:email, number:number}
        });
    }
	
    this.getAllPersons = function getAllUsers(){
        return $http({
          method: 'GET',
          url: 'person/all'
        });
    }

}]);