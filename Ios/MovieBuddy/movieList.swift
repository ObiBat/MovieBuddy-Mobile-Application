//
//  movieList.swift
//  MovieBuddy
//
//  Created by Otgonbileg on 23/10/2023.
//

import UIKit

class movieList: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    
    @IBOutlet weak var list: UITableView!
    
    
    
    var movie : String = ""
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    var movies : [String] = []
    var ind : Int = 5
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return appDelegate.getMovieInfo().count
    }
    
    
    
    
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:UITableViewCell = list.dequeueReusableCell(withIdentifier: "mycell", for: indexPath)
        
        cell.textLabel!.text = appDelegate.getMovieInfo()[indexPath.row]
        
//        let image:UIImage = UIImage(named: "img.png")!
//
//        if (cell.textLabel!.text == "Mobile App Development") {
//            cell.imageView?.image = image
//        }
        
        return cell;
    }
    
    
    // Table view delegate method for handling cell selection
     func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
         
//            movie = movies[indexPath.row]
         
            ind = indexPath.row
   
    
//            if let secondViewController = storyboard?.instantiateViewController(withIdentifier: "movieEdit") as? movieEdit {
//                 secondViewController.selectedData = movie
//                 navigationController?.pushViewController(secondViewController, animated: true)
//             }
         
         
         
     }
    
    

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {


      if let view2 = segue.destination as? movieEdit {

          view2.selectedData = movies[ind]
      }

    }
    
    
    
    
    
    

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        movies = appDelegate.getMovieInfo()
        
    }
    
    
    
    
    func stringToArray(_ input: String) -> [String] {
        // Split the input string by the ", " delimiter
        let components = input.components(separatedBy: ", ")
        return components
    }
    
    
 
    @IBAction func clear_rec(_ sender: Any) {
        appDelegate.removeRecords_movie()
    }
    

    
    
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
