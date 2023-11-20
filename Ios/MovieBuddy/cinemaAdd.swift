//
//  cinemaAdd.swift
//  MovieBuddy
//
//  Created by Otgonbileg on 23/10/2023.
//

import UIKit

class cinemaAdd: UIViewController {

    @IBOutlet weak var location: UITextField!
    @IBOutlet weak var name: UITextField!
    @IBOutlet weak var id: UITextField!
    

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    @IBAction func add_cinema(_ sender: Any) {
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        // call function storePersonInfo in AppDelegate
//        appDelegate.storeMovieData(id: Int(id.text!)!, title: title_movie.text!, directors: director.text!, casts: casts.text!, releaseDate: releaseDate.date)
        
        appDelegate.storeCinemaData(id: Int(id.text!)!, cinemaName: name.text!, location: location.text!)
        
        let alert = UIAlertController(title: "Alert", message: "Item has added successfully!", preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
        
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
