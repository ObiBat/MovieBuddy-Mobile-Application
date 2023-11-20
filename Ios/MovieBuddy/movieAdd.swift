//
//  movieAdd.swift
//  MovieBuddy
//
//  Created by Otgonbileg on 23/10/2023.
//

import UIKit

class movieAdd: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    
    var imagePicker: UIImagePickerController!

    @IBOutlet weak var id: UITextField!
    @IBOutlet weak var title_movie: UITextField!
    @IBOutlet weak var director: UITextField!
    @IBOutlet weak var casts: UITextField!
    @IBOutlet weak var releaseDate: UIDatePicker!
    
    @IBOutlet weak var img: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    

    
    
    
    @IBAction func add(_ sender: Any) {
        
//        let dateFormatter = DateFormatter()
//        dateFormatter.dateFormat = "yyyy-MM-dd" // Choose your desired date format
//        let selectedDate = releaseDate.date
//        let releaseDates = dateFormatter.string(from: selectedDate)
        
        // get the AppDelegate object
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        // call function storePersonInfo in AppDelegate
        appDelegate.storeMovieData(id: Int(id.text!)!, title: title_movie.text!, directors: director.text!, casts: casts.text!, releaseDate: releaseDate.date)
        
        let alert = UIAlertController(title: "Alert", message: "Item has added successfully!", preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertAction.Style.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
        
        
    }
    
    
    
    @IBAction func home(_ sender: Any) {
        
        let viewController = ViewController()
        navigationController?.pushViewController(viewController, animated: true)
    }
    
    
    
    @IBAction func select_photo(_ sender: Any) {
        
        imagePicker = UIImagePickerController()
        imagePicker.delegate = self
        imagePicker.sourceType = .photoLibrary
        imagePicker.allowsEditing = true
        present(imagePicker, animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if let image = info[.editedImage] as? UIImage{
            img.image = image
            dismiss(animated:true, completion: nil)
        }
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
