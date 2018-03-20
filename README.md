# RepoViewer
Android Github repository viewer for Cellpoint Mobile

Parts 1, 2 and 3 are completed and branched.

This project was built following an MVP programming pattern utilizing RxJava2, ApolloGraphQL, and Realm. 
I prefer using Android Starters to set up my projects (http://androidstarters.com/) but due to 
time restrictions and simplicity of the application, I've omitted testing (espresso, robolectric, mockito) 
and dependency injection (dagger 2). I went with MVP primarily because it is the most structured approach 
to creating Android applications. I used ApolloGraphQL because it was one of the only Android GraphQL client libraries available.

The application usage is simple: 
1. Wait for data to load.
2. Tap the search button.
3. Enter search text. Every letter will query for new results via SearchView.OnQueryTextListener.

NOTE: If no data is being returned, the GraphqlService class needs a new Bearer token. Please generate one yourself to test with.

I used the following resources for assistance 

https://www.apollographql.com/docs/ios/downloading-schema.html

https://www.youtube.com/watch?v=9BZjPSUZjak

https://www.youtube.com/watch?v=pgaJSoewOZQ

https://github.com/apollographql/apollo-android

https://developer.github.com/v4/guides/forming-calls/#authenticating-with-graphql

https://www.graph.cool/

https://realm.io/docs/java/latest#adapters


-Gagan Singh
