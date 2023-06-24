import axios  from "axios";
import {ElMessage} from "element-plus";

const defaultError=()=>ElMessage.error("Something wrong, Please contact with admin!");
const defaultFailure=(message)=>ElMessage.warning(message)

function  post(url,data,success,failure=defaultFailure,error=defaultFailure){
    axios.post(url,data,{
        headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        },
        withCredentials:true
    }).then(({data})=>{
        if(data.success){
            success(data.message,data.status)
        }else{
            failure(data.message,data.status)
        }
    }).catch(error)
}
function  get(url,success,failure=defaultFailure,error=defaultFailure){
    axios.get(url,{
        withCredentials:true
    }).then(({data})=>{
        if(data.success){
            success(data.message,data.status)
        }else{
            failure(data.message,data.status)
        }
    }).catch(error)
}
export {get,post}