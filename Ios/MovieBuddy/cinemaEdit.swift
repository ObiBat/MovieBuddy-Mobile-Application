//
//  cinemaEdit.swift
//  MovieBuddy
//
//  Created by Otgonbileg on 23/10/2023.
//

import UIKit

class cinemaEdit: UIViewController {
    
    @IBOutlet weak var name: UITextField!
    
    @IBOutlet weak var location: UITextField!
    var selectedData :String?
    var resultArray: [String] = []
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let sdata = selectedData {
            
            resultArray = stringToArray(sdata)
            
            name.text = resultArray[0]
            location.text = resultArray[1]
            
        } else {
            print("Data missing")
        }
    }
    

    func stringToArray(_ input: String) -> [String] {
        // Split the input string by the ", " delimiter
        let components = input.components(separatedBy: ", ")
        return components
    }

    @IBAction func edit(_ sender: Any) {
        
        let id: Int = Int(resultArray[0])!
        
        appDelegate.updateCinemaDetails(id: id, cinemaName: name.text!, location: location.text!)
        
        let alert = UIAlertController(title: "Alert", message: "Item has edited successfully!", preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}
