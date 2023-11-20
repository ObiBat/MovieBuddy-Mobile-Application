//
//  cinemaList.swift
//  MovieBuddy
//
//  Created by Otgonbileg on 23/10/2023.
//

import UIKit

class cinemaList: UIViewController, UITableViewDelegate, UITableViewDataSource  {
    
    

    @IBOutlet weak var list: UITableView!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    var ind : Int = 0
    
    var cinemas : [String] = []
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return appDelegate.getCinemaInfo().count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:UITableViewCell = list.dequeueReusableCell(withIdentifier: "mycell", for: indexPath)
        
        cell.textLabel!.text = appDelegate.getCinemaInfo()[indexPath.row]
        
//        let image:UIImage = UIImage(named: "img.png")!
//
//        if (cell.textLabel!.text == "Mobile App Development") {
//            cell.imageView?.image = image
//        }
        
        
        
        return cell;
        
    }
    
    
    // Table view delegate method for handling cell selection
     func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
         
         
            ind = indexPath.row
         
         return
   

         
     }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {


      if let view2 = segue.destination as? cinemaEdit {

          view2.selectedData = cinemas[ind]
          
      }

    }
    

 
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        cinemas = appDelegate.getCinemaInfo()
        

        // Do any additional setup after loading the view.
    }
    

    @IBAction func clear_rec(_ sender: Any) {
        
        appDelegate.removeRecords_cinema()
    }
    
    
    
    

}
