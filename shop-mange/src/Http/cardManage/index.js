import cardApi from './apiGether'
import HTTP from './../http'
var http = new HTTP()
export default {
  getCardByPageSize (query, success, error) {
    http.get(cardApi.byPageSize, {
      current: query.current,
      size: query.size
    })
      .then(success, error)
  },
  getCardByPhoneAndPageSize (query, success, error) {
    http.get(cardApi.byPhoneAndSize, {
      mobile: query.phone,
      current: query.current,
      size: query.size
    })
      .then(success, error)
  },
  removeCardById ({
    id
  }, success, error) {
    http.get(cardApi.deleteCard, {
      id: id
    })
      .then(success)
      .catch(error)
  },
  addCustomerCard (cardInfo, success, error) {
    http.post(cardApi.increasecard, cardInfo)
      .then(success, error)
  }
}
