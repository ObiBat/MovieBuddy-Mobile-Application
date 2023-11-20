//
//  AppDelegate.swift
//  MovieBuddy
//
//  Created by Otgonbileg on 17/10/2023.
//

import UIKit
import CoreData

@main
class AppDelegate: UIResponder, UIApplicationDelegate {



    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }

    // MARK: - Core Data stack

    lazy var persistentContainer: NSPersistentContainer = {
        /*
         The persistent container for the application. This implementation
         creates and returns a container, having loaded the store for the
         application to it. This property is optional since there are legitimate
         error conditions that could cause the creation of the store to fail.
        */
        let container = NSPersistentContainer(name: "MovieBuddy")
        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                 
                /*
                 Typical reasons for an error here include:
                 * The parent directory does not exist, cannot be created, or disallows writing.
                 * The persistent store is not accessible, due to permissions or data protection when the device is locked.
                 * The device is out of space.
                 * The store could not be migrated to the current model version.
                 Check the error message to determine what the actual problem was.
                 */
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
        return container
    }()

    // MARK: - Core Data Saving support

    func saveContext () {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
    }
    
    // all functions for core data are here
    func getContext () -> NSManagedObjectContext {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        return appDelegate.persistentContainer.viewContext
    }
    
    
    
    //my code for DB
    
    func storeMovieData (id: Int, title: String, directors: String, casts: String, releaseDate: Date) {
        let context = getContext()
        
        //retrieve the entity that we just created
        let entity =  NSEntityDescription.entity(forEntityName: "Movies", in: context)
        
        let transc = NSManagedObject(entity: entity!, insertInto: context)
        
        //set the entity values
        transc.setValue(id, forKey: "id")
        transc.setValue(title, forKey: "title")
        transc.setValue(directors, forKey: "director")
        transc.setValue(casts, forKey: "casts")
        transc.setValue(releaseDate, forKey: "releaseDate")
        
        // Set the poster image data if available
//        if let imageData = posterImageData {
//            transc.setValue(imageData, forKey: "poster")
//        }
        

        
        //save the object
        do {
            try context.save()
            print("Completed.")
        } catch let error as NSError  {
            print("Could not save \(error), \(error.userInfo)")
        } catch {
            
        }
    }
    
    func getMovieInfo () -> [String] {
        
        var movie : [String] = [];
        
        var info: String
     
        
        //create a fetch request, telling it about the entity
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Movies")
        
        do {
            //go get the results
            let searchResults = try getContext().fetch(fetchRequest)
            
            //I like to check the size of the returned results!
            print ("num of results = \(searchResults.count)")
            
            //You need to convert to NSManagedObject to use 'for' loops
            for trans in searchResults as [NSManagedObject] {
                
                let dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "yyyy-MM-dd" // You can choose any desired date format
                
                
                let id = String(trans.value(forKey: "id") as! Int)
                let title = trans.value(forKey: "title") as! String
                let directors = trans.value(forKey: "director") as! String
                let casts = trans.value(forKey: "casts") as! String
                let releaseDate = dateFormatter.string(from: trans.value(forKey: "releaseDate") as! Date)

                info = id + ", " + title + ", " + directors + ", " + casts + ", " + releaseDate
                
                movie.append(info)
            }
        } catch {
            print("Error with request: \(error)")
        }
        return movie;
    }
    
    
    
    func storeCinemaData (id: Int, cinemaName: String, location: String) {
        let context = getContext()
        
        //retrieve the entity that we just created
        let entity =  NSEntityDescription.entity(forEntityName: "Cinemas", in: context)
        
        let transc = NSManagedObject(entity: entity!, insertInto: context)
        
        //set the entity values
        transc.setValue(id, forKey: "id")
        transc.setValue(cinemaName, forKey: "cinemaName")
        transc.setValue(location, forKey: "location")


        
        //save the object
        do {
            try context.save()
            print("Completed.")
        } catch let error as NSError  {
            print("Could not save \(error), \(error.userInfo)")
        } catch {
            
        }
    }
    
    func getCinemaInfo () -> [String] {
        
        var cinema : [String] = [];
        
        var info: String
     
        
        //create a fetch request, telling it about the entity
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Cinemas")
        
        do {
            //go get the results
            let searchResults = try getContext().fetch(fetchRequest)
            
            //I like to check the size of the returned results!
            print ("num of results = \(searchResults.count)")
            
            //You need to convert to NSManagedObject to use 'for' loops
            for trans in searchResults as [NSManagedObject] {
                
                let dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "yyyy-MM-dd" // You can choose any desired date format
                
                
                let id = String(trans.value(forKey: "id") as! Int)
                let cinemaName = trans.value(forKey: "cinemaName") as! String
                let location = trans.value(forKey: "location") as! String


                info = id + ", " + cinemaName + " " + location
                
                cinema.append(info)
            }
        } catch {
            print("Error with request: \(error)")
        }
        return cinema;
    }
    
    
    
    
    
    func deleteMovieRecord(id: Int) {
        let context = getContext()
        
        // Create a fetch request to find the movie record with the given ID
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Movies")
        fetchRequest.predicate = NSPredicate(format: "id == %d", id)
        
        do {
            // Fetch the matching movie record
            if let movieToDelete = try context.fetch(fetchRequest).first {
                // Delete the movie record
                context.delete(movieToDelete)
                
                // Save the changes
                try context.save()
                print("Movie record with ID \(id) deleted.")
            } else {
                print("Movie record with ID \(id) not found.")
            }
        } catch {
            print("Error deleting movie record: \(error)")
        }
    }
    
    
    //remove all records
    func removeRecords_movie () {
        let context = getContext()
        // delete everything in the table Person
        let deleteFetch = NSFetchRequest<NSFetchRequestResult>(entityName: "Movies")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: deleteFetch)
        
        do {
            try context.execute(deleteRequest)
            try context.save()
        } catch {
            print ("There was an error")
        }
    }
    
    //remove all records
    func removeRecords_cinema () {
        let context = getContext()
        // delete everything in the table Person
        let deleteFetch = NSFetchRequest<NSFetchRequestResult>(entityName: "Cinemas")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: deleteFetch)
        
        do {
            try context.execute(deleteRequest)
            try context.save()
        } catch {
            print ("There was an error")
        }
    }
    
    
    func updateMovieDetails(id: Int, title: String, directors: String, casts: String, releaseDate: Date) {
        let context = getContext()
        
        // Create a fetch request to find the movie record with the given ID
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Movies")
        fetchRequest.predicate = NSPredicate(format: "id == %d", id)
        
        do {
            // Fetch the matching movie record
            if let movieToUpdate = try context.fetch(fetchRequest).first {
                // Update the movie record
                movieToUpdate.setValue(title, forKey: "title")
                movieToUpdate.setValue(directors, forKey: "director")
                movieToUpdate.setValue(casts, forKey: "casts")
                movieToUpdate.setValue(releaseDate, forKey: "releaseDate")
                
                // Save the changes
                try context.save()
                print("Movie record with ID \(id) updated.")
            } else {
                print("Movie record with ID \(id) not found.")
            }
        } catch {
            print("Error updating movie details: \(error)")
        }
    }
    
    
    func updateCinemaDetails(id: Int, cinemaName: String, location: String) {
        let context = getContext()
        
        // Create a fetch request to find the cinema record with the given ID
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Cinemas")
        fetchRequest.predicate = NSPredicate(format: "id == %d", id)
        
        do {
            // Fetch the matching cinema record
            if let cinemaToUpdate = try context.fetch(fetchRequest).first {
                // Update the cinema record
                cinemaToUpdate.setValue(cinemaName, forKey: "cinemaName")
                cinemaToUpdate.setValue(location, forKey: "location")
                
                // Save the changes
                try context.save()
                print("Cinema record with ID \(id) updated.")
            } else {
                print("Cinema record with ID \(id) not found.")
            }
        } catch {
            print("Error updating cinema details: \(error)")
        }
    }

}

