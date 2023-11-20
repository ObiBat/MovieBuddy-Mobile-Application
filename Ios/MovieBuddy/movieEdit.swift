//
//  movieEdit.swift
//  MovieBuddy
//
//  Created by Otgonbileg on 23/10/2023.
//

import UIKit

class movieEdit: UIViewController {
    
    
    var selectedData :String?

    
    var resultArray: [String] = []
    
    @IBOutlet weak var titless: UITextField!
    @IBOutlet weak var director: UITextField!
    @IBOutlet weak var date: UIDatePicker!
    @IBOutlet weak var casts: UITextField!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let selectedDatas = selectedData {
            resultArray = stringToArray(selectedDatas)
            
                titless.text = resultArray[1]
                director.text = resultArray[2]
                casts.text = resultArray[3]
//             else {
//                titless.text = "Data Incomplete"
//                director.text = "Data Incomplete"
//                casts.text = "Data Incomplete"
//            }
        } else {
            // Handle the case where selectedData is nil
            print("Data missing")
            
        }
        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    func stringToArray(_ input: String) -> [String] {
        // Split the input string by the ", " delimiter
        let components = input.components(separatedBy: ", ")
        return components
    }


    @IBAction func edit(_ sender: Any) {
        
        
        let id: Int = Int(resultArray[0])!
        
        appDelegate.updateMovieDetails(id: id, title: titless.text!, directors: director.text!, casts: casts.text!, releaseDate: date.date)
        
        let alert = UIAlertController(title: "Alert", message: "Item has edited successfully!", preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
        
    }
    
}
