# Things used here

- Typescript
- React Redux
- yarn package manager

# React Redux

## Reducer

- Function that receives the old (previous) **state** and an **action** as arguments, then returns as output the updated state.
- Reducers can only handle synchronouse code (so e.g. no HTTP calls)

## Store

- Holds the state tree of the react app => Here lives the state of the js app
- Your can see it as an big js object
- To create a store, we need to create reducers and pass them in as arguments

## ExtraReducers and AsnycThunk

- AT's are needed when you want to make async calls (e.g. HTTP calls)
- AT's are functions which accept a string and a Callback method returning a Promise
- Since AT's don't know which Data is fetched, you can't handle them in a normal reducer, therefore you need extrareducers

## Middleware

- Redux Middleware allows you to intercept every action sent to the reducer so you can make changes to the action or cancel the action.
