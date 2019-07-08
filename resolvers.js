const db = require('./db')
const Query = {
  
  
   students:() => db.students.list(),

 
   studentById:(root,args,context,info) => {

      return db.students.get(args.id);
   }
}

const Student = {
   fullName:(root,args,context,info) => {
      return root.firstName+":"+root.lastName
   },
   college:(root) => {
      return db.colleges.get(root.collegeId);
   }
}

module.exports = {Query,Student}